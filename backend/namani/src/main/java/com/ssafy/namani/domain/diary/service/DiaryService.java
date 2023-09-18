package com.ssafy.namani.domain.diary.service;

import com.ssafy.namani.domain.diary.dto.request.DiaryDetailRequestDto;
import com.ssafy.namani.domain.diary.dto.request.DiaryListRequestDto;
import com.ssafy.namani.domain.diary.dto.request.DiaryMonthlyAnalysisRequestDto;
import com.ssafy.namani.domain.diary.dto.request.DiaryRegistRequestDto;
import com.ssafy.namani.domain.diary.dto.request.DiaryUpdateContentRequestDto;
import com.ssafy.namani.domain.diary.dto.response.DiaryDetailResponseDto;
import com.ssafy.namani.domain.diary.dto.response.DiaryListResponseDto;
import com.ssafy.namani.domain.diary.dto.response.DiaryMonthlyAnalysisResponseDto;
import com.ssafy.namani.domain.diary.dto.response.DiaryResponseDto;
import com.ssafy.namani.global.response.BaseException;

import java.util.List;
import java.util.UUID;

public interface DiaryService {

    DiaryListResponseDto getCalendar(UUID memberId, DiaryListRequestDto diaryListRequestDto) throws BaseException;

    DiaryResponseDto registDiary(String accessToken, List<DiaryRegistRequestDto> diaryRegistRequestDtoList) throws BaseException;

    DiaryDetailResponseDto detailDiary(String accessToken, DiaryDetailRequestDto diaryDetailRequestDto) throws BaseException;

    void updateDiary(Long id, DiaryUpdateContentRequestDto diaryUpdateContentRequestDto) throws BaseException;

    DiaryMonthlyAnalysisResponseDto getMonthlyAnalysis(UUID memberId, DiaryMonthlyAnalysisRequestDto diaryMonthlyAnalysisRequestDto) throws BaseException;
}
