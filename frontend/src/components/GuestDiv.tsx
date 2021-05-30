import React, { useState } from 'react';
import styled from 'styled-components';
import Icons from './Icons';
import theme from '../styles/theme';
import { useRecoilState } from 'recoil';
import { filterDisplayAtom, guestsDataAtom } from '../recoil/atom';

interface IGuest {
  label: string;
  caption: string;
}

const GuestDiv: React.FC<IGuest> = props => {
  const [guestsData, setguestsData] = useRecoilState<any>(guestsDataAtom);
  const [filterDisplay, setFilterDisplay] =
    useRecoilState<any>(filterDisplayAtom);

  const onClick = (type: string, guest: string): void => {
    const guestsCopy = { ...guestsData };
    const MAXIMUM_COUNT = 8;
    const MINIMUM_COUNT = 0;

    type === 'plus' &&
      guest === ('어린이' || '유아') &&
      guestsCopy.성인 === 0 &&
      (guestsCopy.성인 += 1);

    type === 'plus' &&
      guestsCopy[`${guest}`] < MAXIMUM_COUNT &&
      (guestsCopy[`${guest}`] += 1);

    type === 'minus' &&
      guestsCopy[`${guest}`] > MINIMUM_COUNT &&
      (guestsCopy[`${guest}`] -= 1);

    const guestDisplay =
      guestsCopy.유아 > 0
        ? `게스트 ${guestsCopy.성인 + guestsCopy.어린이}명 유아 ${
            guestsCopy.유아
          }명`
        : `게스트 ${guestsCopy.성인 + guestsCopy.어린이}명 `;

    setguestsData({
      ...guestsCopy,
    });
    setFilterDisplay({
      ...filterDisplay,
      인원: guestDisplay,
    });
  };

  return (
    <>
      <GuestWrapper>
        <div>
          <Label>{props.label}</Label>
          <Caption>{props.caption}</Caption>
        </div>
        <GuestCount>
          <div
            onClick={event => {
              onClick('minus', props.label);
            }}>
            <Icons type="minus-circle" />
          </div>
          <Span>{guestsData[`${props.label}`]}</Span>
          <div
            onClick={event => {
              onClick('plus', props.label);
            }}>
            <Icons type="plus-circle" />
          </div>
        </GuestCount>
      </GuestWrapper>
    </>
  );
};

const GuestWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  width: 272px;
`;

const Label = styled.div`
  width: 80px;
  height: 23px;

  font-weight: bold;
  font-size: 16px;
  display: flex;
  align-items: center;

  color: ${theme.colors.black};
`;

const Caption = styled.div`
  width: 80px;
  height: 20px;

  font-size: 14px;
  display: flex;
  align-items: center;

  color: ${theme.colors.grey3};
`;

const GuestCount = styled.div`
  display: flex;
  align-items: center;
`;

const Span = styled.div`
  width: 12px;
  height: 29px;
  padding: 0 30px 0 24px;

  font-weight: bold;
  font-size: 20px;

  display: flex;
  align-items: center;

  color: ${theme.colors.grey1}; ;
`;

export default GuestDiv;
