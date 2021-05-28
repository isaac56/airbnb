import React from 'react';
import styled from 'styled-components';
import GuestDiv from './GuestDiv';
import theme from '../styles/theme';

const GuestModal = () => {
  const guestText = [
    ['성인', '만 13세 이상'],
    ['어린이', '만 2~12세'],
    ['유아', '만 2세 미만'],
  ];

  return (
    <>
      <Div className="guests">
        {guestText.map(([key, value], i, { length }) => {
          return (
            <>
              <GuestDiv key={i} label={key} caption={value} />
              {!(i + 1 === length) && <Line />}
            </>
          );
        })}
      </Div>
    </>
  );
};

const Div = styled.div`
  position: absolute;
  width: 400px;
  height: 355px;

  box-shadow: 0px 4px 10px rgba(51, 51, 51, 0.1),
    0px 0px 4px rgba(51, 51, 51, 0.05);
  border-radius: 40px;
  background: ${theme.colors.white};
  ${theme.alignCenter}
  flex-direction:column;
  div {
    margin: 6px 0;
  }
`;

const Line = styled.div`
  width: 272px;
  height: 1px;

  background: #c4c4c4;
`;
export default GuestModal;
