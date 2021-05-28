import React from 'react';
import styled from 'styled-components';
import theme from '../styles/theme';

const PriceModal = () => {
  return (
    <Div className="price">
      <PriceTitle>가격 범위</PriceTitle>
    </Div>
  );
};

const Div = styled.div`
  width: 493px;
  height: 355px;

  background: ${theme.colors.white};

  box-shadow: 0px 4px 10px rgba(51, 51, 51, 0.1),
    0px 0px 4px rgba(51, 51, 51, 0.05);
  border-radius: 40px;
`;

const PriceTitle = styled.div`
  width: 63px;
  height: 23px;

  font-weight: bold;
  font-size: 16px;
  line-height: 23px;
  display: flex;
  align-items: center;

  color: ${theme.colors.black};
`;
export default PriceModal;
