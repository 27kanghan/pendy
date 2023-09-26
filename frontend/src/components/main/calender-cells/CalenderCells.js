import React, { useState } from 'react';
import './CalenderCells.css';
import { format } from 'date-fns';
import { startOfMonth, endOfMonth, startOfWeek, endOfWeek } from 'date-fns';
import { isSameMonth, isSameDay, addDays } from 'date-fns';

// 캘린더 cell
const CalenderCells = ({ currentMonth, diaries }) => {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const monthStart = startOfMonth(currentMonth);
  const monthEnd = endOfMonth(monthStart);
  const startDate = startOfWeek(monthStart);
  const endDate = endOfWeek(monthEnd);

  const rows = [];
  let days = [];
  let day = startDate;
  let formattedDate = '';

  // 날짜 선택
  const onDateClick = (day) => {
    setSelectedDate(day);
  };

  //일기가 있을 경우 점 표시
  const getDotStyle = (day) => {
    const hasDiary = diaries.some((diary) =>
      isSameDay(new Date(diary.regDate), day),
    );
    return hasDiary;
  };

  while (day <= endDate) {
    for (let i = 0; i < 7; i++) {
      formattedDate = format(day, 'd');
      const cloneDay = day;
      days.push(
        <div
          className={`col cell ${
            !isSameMonth(day, monthStart)
              ? 'disabled'
              : isSameDay(day, selectedDate)
              ? 'selected'
              : format(currentMonth, 'M') !== format(day, 'M')
              ? 'not-valid'
              : 'valid'
          }`}
          key={day}
          onClick={() => onDateClick(cloneDay)}
        >
          <span
            className={
              format(currentMonth, 'M') !== format(day, 'M')
                ? 'text not-valid'
                : ''
            }
          >
            {formattedDate}
          </span>
          {getDotStyle(day) && (
            <div className="dot-container">
              <div className="dot"></div>
            </div>
          )}
        </div>,
      );
      day = addDays(day, 1);
    }
    rows.push(
      <div className="calender-row" key={day}>
        {days}
      </div>,
    );
    days = [];
  }
  return <div className="calender-body">{rows}</div>;
};

export default CalenderCells;
