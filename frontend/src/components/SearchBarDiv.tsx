import React from 'react';
import theme from '../styles/theme';
import styled from 'styled-components';
import Icons from './Icons';
import { useRecoilState } from 'recoil';
import {
  filterDisplayData,
  checkInOutData,
  priceData,
  guestsData,
} from '../recoil/atom';

interface Ispan {
  label: string;
  text: string;
  onClick(className: string): void;
}

const SearchBarDiv: React.FC<Ispan> = props => {
  const [filterDisplay, setFilterDisplay] =
    useRecoilState<any>(filterDisplayData);
  const [dates, setDates] = useRecoilState<any>(checkInOutData);
  const [prices, setPrices] = useRecoilState<any>(priceData);
  const [guests, setGuests] = useRecoilState<any>(guestsData);

  const resetDates = () => {
    const filterDisplayCopy = { ...filterDisplay };
    const dataCopy = { ...dates };
    const priceCopy = { ...prices };
    const guestsCopy = { ...guests };

    switch (props.label) {
      case '체크인':
      case '체크아웃':
        filterDisplayCopy[`${props.label}`] = '입력 날짜';
        dataCopy[`${props.label}`] = null;
        setDates({ ...dataCopy });
        break;
      case '요금':
        filterDisplayCopy[`${props.label}`] = '금액대 설정';
        Object.keys(priceCopy).forEach(i => (priceCopy[i] = 0));
        setPrices({ ...priceCopy });
        break;
      case '인원':
        filterDisplayCopy[`${props.label}`] = '게스트 추가';
        Object.keys(guestsCopy).forEach(i => (guestsCopy[i] = 0));
        setGuests({ ...guestsCopy });
        break;
      default:
        break;
    }

    setFilterDisplay({
      ...filterDisplay,
      ...filterDisplayCopy,
    });
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
      {((props.label === '체크인' && dates.체크인) ||
        (props.label === '체크아웃' && dates.체크아웃) ||
        (props.label === '요금' && prices.최저가) ||
        (props.label === '인원' && guests.성인 > 0)) && (
        <IconWrapper className="x-circle" onClick={resetDates}>
          <Icons type="x-circle" />
        </IconWrapper>
      )}
    </Div>
  );
};

const Div = styled.div`
  position: relative;
  padding: 0 5% 0 4%;
  display: flex;
  flex-direction: flex-start;
  cursor: pointer;
  &:hover {
    background-color: ${theme.colors.grey6};
    border-radius: 60px;
    z-index: 2;
  }
  &.인원 > .x-circle {
    position: absolute;
    right: 15px;
  }
  &.요금 > .x-circle {
    position: absolute;
    right: 13px;
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

export default SearchBarDiv;
