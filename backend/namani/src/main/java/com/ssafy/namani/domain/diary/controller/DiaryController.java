package com.ssafy.namani.domain.diary.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.namani.domain.diary.dto.request.DiaryDetailRequestDto;
import com.ssafy.namani.domain.diary.dto.request.DiaryListRequestDto;
import com.ssafy.namani.domain.diary.dto.request.DiaryMonthlyAnalysisRequestDto;
import com.ssafy.namani.domain.diary.dto.request.DiaryRegistRequestDto;
import com.ssafy.namani.domain.diary.dto.request.DiaryUpdateContentRequestDto;
import com.ssafy.namani.domain.diary.dto.response.DiaryDetailResponseDto;
import com.ssafy.namani.domain.diary.dto.response.DiaryListResponseDto;
import com.ssafy.namani.domain.diary.dto.response.DiaryMonthlyAnalysisResponseDto;
import com.ssafy.namani.domain.diary.dto.response.DiaryResponseDto;
import com.ssafy.namani.domain.diary.service.DiaryService;
import com.ssafy.namani.domain.jwt.service.JwtService;
import com.ssafy.namani.global.response.BaseException;
import com.ssafy.namani.global.response.BaseResponse;
import com.ssafy.namani.global.response.BaseResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {

	private final DiaryService diaryService;
	private final BaseResponseService baseResponseService;
	private final JwtService jwtService;

	/**
	 * 해당 월의 달력 정보를 불러오는 API
	 *
	 * @param accessToken
	 * @param diaryListRequestDto
	 * @return diaryListResponseDto
	 */
	@PostMapping("/calendar")
	public BaseResponse<Object> getCalendar(@RequestHeader(value = "accessToken") String accessToken,
		@RequestBody DiaryListRequestDto diaryListRequestDto) {
		try {
			DiaryListResponseDto diaryListResponseDto = diaryService.getCalendar(accessToken, diaryListRequestDto);
			return baseResponseService.getSuccessResponse(diaryListResponseDto);
		} catch (BaseException e) {
			return baseResponseService.getFailureResponse(e.status);
		}
	}

	/**
	 * 감정 등록 -> 일기 생성 -> 일기 등록을 하는 API
	 *
	 * @param accessToken
	 * @param diaryRegistRequestDtoList
	 * @return diaryResponseDto
	 */
	@PostMapping("/regist")
	public BaseResponse<Object> registDiary(String accessToken,
		@RequestBody List<DiaryRegistRequestDto> diaryRegistRequestDtoList) {
		try {
			DiaryResponseDto diaryResponseDto = diaryService.registDiary(accessToken, diaryRegistRequestDtoList);
			return baseResponseService.getSuccessResponse(diaryResponseDto);
		} catch (BaseException e) {
			return baseResponseService.getFailureResponse(e.status);
		}
	}

	/**
	 * 일기를 조회하는 API
	 *
	 * @param accessToken
	 * @param diaryDetailRequestDto
	 * @return diaryDetailResponseDto
	 */
	@PostMapping("/after")
	public BaseResponse<Object> detailDiary(String accessToken,
		@RequestBody DiaryDetailRequestDto diaryDetailRequestDto) {
		try {
			DiaryDetailResponseDto diaryDetailResponseDto = diaryService.detailDiary(accessToken,
				diaryDetailRequestDto);
			return baseResponseService.getSuccessResponse(diaryDetailResponseDto);
		} catch (BaseException e) {
			return baseResponseService.getFailureResponse(e.status);
		}
	}

	/**
	 * 일기의 내용을 수정하는 API
	 *
	 * @param id
	 * @param diaryUpdateContentRequestDto
	 * @return diaryResponseDto
	 */
	@PutMapping("/{id}")
	public BaseResponse<Object> updateDiary(@PathVariable("id") Long id,
		@RequestBody DiaryUpdateContentRequestDto diaryUpdateContentRequestDto) {
		try {
			DiaryResponseDto diaryResponseDto = diaryService.updateDiary(id, diaryUpdateContentRequestDto);
			return baseResponseService.getSuccessResponse(diaryResponseDto);
		} catch (BaseException e) {
			return baseResponseService.getFailureResponse(e.status);
		}
	}

	/**
	 * 해당 월의 월간 분석 정보를 불러오는 API
	 *
	 * @param accessToken
	 * @param diaryMonthlyAnalysisRequestDto
	 * @return diaryMonthlyAnalysisResponseDto
	 */
	@PostMapping("/monthly-analysis")
	public BaseResponse<Object> getMonthlyAnalysis(@RequestHeader(value = "accessToken") String accessToken,
		@RequestBody DiaryMonthlyAnalysisRequestDto diaryMonthlyAnalysisRequestDto) {
		UUID memberIdFromToken = jwtService.getMemberIdFromToken(accessToken);
		try {
			DiaryMonthlyAnalysisResponseDto diaryMonthlyAnalysisResponseDto = diaryService.getMonthlyAnalysis(
				memberIdFromToken, diaryMonthlyAnalysisRequestDto);
			return baseResponseService.getSuccessResponse(diaryMonthlyAnalysisResponseDto);
		} catch (BaseException e) {
			return baseResponseService.getFailureResponse(e.status);
		}
	}
}
