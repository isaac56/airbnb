import React from 'react';
import styled from 'styled-components';
import theme from '../styles/theme';

interface Idate {
  month: number;
}

const SingleCalendar: React.FC<Idate> = props => {
  const baseDate: Date = new Date();
  baseDate.setMonth(baseDate.getMonth() + props.month);

  const firstDayIndex: number = new Date(
    baseDate.getFullYear(),
    baseDate.getMonth(),
    1
  ).getDay();

  const baseMonthLastDay: Date = new Date(
    baseDate.getFullYear(),
    baseDate.getMonth() + 1,
    0
  );

  const lastDayIndex = baseMonthLastDay.getDay();

  const lastDayOfMonth: number = baseMonthLastDay.getDate();

  const calendarTitle: string = `${baseDate.getFullYear()}년 ${
    baseDate.getMonth() + 1
  }월`;

  const weekdays: string[] = ['일', '월', '화', '수', '목', '금', '토'];

  return (
    <SingleWrapper>
      <Month>{calendarTitle}</Month>
      <Week>
        {weekdays.map((day, i) => {
          return <p key={i}>{day}</p>;
        })}
      </Week>
      <Day>
        {Array.from({ length: firstDayIndex }, (blank, i) => (
          <p key={i}>&nbsp;</p>
        ))}
        {Array.from({ length: lastDayOfMonth }, (day, i) => (
          <p key={i}>{i + 1}</p>
        ))}
      </Day>
    </SingleWrapper>
  );
};

const SingleWrapper = styled.div`
  box-sizing: content-box;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 40px 50px 50px 50px;

  width: 336px;
  height: 336px;
  background-color: ${theme.colors.white};
  div {
    width: 336px;
    height: 24px;
    p {
      width: 48px;
      height: 24px;
      font-size: 12px;
      ${theme.alignCenter}
    }
  }
`;

const Month = styled.div`
  margin-bottom: 10px;
  text-align: center;
  font-weight: bold;
  font-size: 16px;
  color: ${theme.colors.black};
`;

const Week = styled.div`
  display: flex;
  margin: 12px 0px;
  p {
    color: ${theme.colors.grey3};
  }
`;

const Day = styled.div`
  display: flex;
  margin: 4px 0px;
  flex-wrap: wrap;
  p {
    cursor: pointer;
    color: ${theme.colors.black};
    font-weight: bold;
  }
`;

export default SingleCalendar;
