import React from 'react';
import theme from '../styles/theme';
import styled from 'styled-components';
import Icons from './Icons';
import { useRecoilState } from 'recoil';
import { filterAtom, datesAtom } from '../recoil/atom';

interface Ispan {
  label: string;
  text: string;
  onClick(className: string): void;
}

const SearchBarFilterStyle: React.FC<Ispan> = props => {
  const [filter, setFilterContents] = useRecoilState<any>(filterAtom);
  const [checkInOutDates, setCheckInOutDates] = useRecoilState(datesAtom);

  const resetDates = () => {
    switch (props.label) {
      case '체크인':
        setFilterContents({
          ...filter,
          체크인: '입력 날짜',
        });
        setCheckInOutDates({
          ...checkInOutDates,
          checkIn: {
            year: null,
            month: null,
            day: null,
          },
        });
        return;
      case '체크아웃':
        setFilterContents({
          ...filter,
          체크아웃: '입력 날짜',
        });
        setCheckInOutDates({
          ...checkInOutDates,
          checkOut: {
            year: null,
            month: null,
            day: null,
          },
        });
        return;
      default:
        break;
    }
  };

  return (
    <Div
      className={props.label}
      onClick={() => {
        props.onClick(props.label);
      }}>
      <Input>
        <Label>{props.label}</Label>
        <Text>{props.text}</Text>
      </Input>
      {((props.label === '체크인' && checkInOutDates.checkIn) ||
        (props.label === '체크아웃' && checkInOutDates.checkOut)) && (
        <IconWrapper onClick={resetDates}>
          <Icons className="x-circle" type="x-circle" />
        </IconWrapper>
      )}
    </Div>
  );
};

const Div = styled.div`
  position: relative;
  padding: 0 4%;
  display: flex;
  flex-direction: flex-start;
  cursor: pointer;
  &:hover {
    background-color: ${theme.colors.grey6};
    border-radius: 60px;
    z-index: 2;
  }
`;

const Input = styled.div`
  display: flex;
  flex-direction: column;
  padding: 16px 6px 16px 6px;
`;

const Label = styled.span`
  color: ${theme.colors.black};
  font-size: 12px;
  font-weight: bold;
  margin-bottom: 10px;
`;

const Text = styled.span`
  color: ${theme.colors.grey2};
  font-size: 16px;
`;

const IconWrapper = styled.div`
  position: absolute;
  right: 40px;
  top: 25px;
`;

export default SearchBarFilterStyle;
