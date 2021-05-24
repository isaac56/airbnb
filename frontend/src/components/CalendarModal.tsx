import React from 'react';
import styled from 'styled-components';
import theme from '../styles/theme';

const CalendarModal = () => {
  return (
    <CalenadrWrapper className="calendar">
      <>
        <SingleWrapper>
          <Month>2021년 5월</Month>
          <Week>
            <p>일</p>
            <p>월</p>
            <p>화</p>
            <p>수</p>
            <p>목</p>
            <p>금</p>
            <p>토</p>
          </Week>
        </SingleWrapper>
        <SingleWrapper></SingleWrapper>
      </>
    </CalenadrWrapper>
  );
};

const SingleWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin: 40px 25px 50px 50px;

  width: 336px;
  height: 336px;
  border: 1px solid black;
  background-color: ${theme.colors.white};
`;

const CalenadrWrapper = styled.div`
  width: 916px;
  height: 512px;
  margin: 10px;
  padding: 50px;
  background: #ffffff;
  box-shadow: 0px 4px 10px rgba(51, 51, 51, 0.1),
    0px 0px 4px rgba(51, 51, 51, 0.05);
  border-radius: 40px;
  display: flex;
`;

const Month = styled.div`
  width: 100%;
  text-align: center;

  font-style: normal;
  font-weight: bold;
  font-size: 16px;

  color: ${theme.colors.black};
`;

const Week = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  p {
    padding: 0 15px;
    font-size: 12px;
    color: #828282;
  }
`;

export default CalendarModal;
