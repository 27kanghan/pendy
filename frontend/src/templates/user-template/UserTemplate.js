import React, { useState } from 'react';
import './UserTemplate.css';
import { addMonths, subMonths } from 'date-fns';

import CalenderHeader from '../../components/main/calender-header/CalenderHeader';
import CalenderDays from '../../components/main/calender-days/CalenderDays';
import CalenderCells from '../../components/main/calender-cells/CalenderCells';
import DoughnutChart from '../../components/common/doughnut-chart/DoughnutChart';

//유저 전용 메인 페이지
const UserTemplate = () => {
  const [currentMonth, setCurrentMonth] = useState(new Date());
  console.log('usertemplate ' + currentMonth);

  // 달력을 이전달로 넘기는 기능
  const prevMonth = () => {
    setCurrentMonth(subMonths(currentMonth, 1));
  };

  //달력을 다음 달로 넘기는 기능
  const nextMonth = () => {
    setCurrentMonth(addMonths(currentMonth, 1));
  };

  return (
    <div className="user">
      <div className="calender-container">
        <div className="calender-content">
          <CalenderHeader
            currentMonth={currentMonth}
            prevMonth={prevMonth}
            nextMonth={nextMonth}
          />
          <CalenderDays />
          <CalenderCells currentMonth={currentMonth} />
        </div>
      </div>
      <div className="chart-container">
        <div className="chart-content">
          <DoughnutChart />
        </div>
      </div>
    </div>
  );
};

export default UserTemplate;