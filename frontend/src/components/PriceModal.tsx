import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import theme from '../styles/theme';
import PriceSlider from './PriceSlider';
import CircularProgress from '@material-ui/core/CircularProgress';
import { makeStyles } from '@material-ui/core/styles';
import { useRecoilValue } from 'recoil';
import { checkInOutData, priceData } from '../recoil/atom';
import useFetch from './UseFetch';

const PriceModal = () => {
  const [minPrice, setMinPrice] = useState(0);
  const [maxPrice, setMaxPrice] = useState(0);
  const prices = useRecoilValue(priceData);
  const dates = useRecoilValue(checkInOutData);
  const classes = useStyles();

  const { isLoading, data, error } = useFetch('userFilter', {
    checkIn: dates.체크인,
    checkOut: dates.체크아웃,
  });

  const pricesByDates = data?.reduce(
    (acc: number[], cur: { [key: string]: any }) => {
      return [...acc, cur.daily_fee];
    },
    []
  );

  useEffect(() => {
    if (Array.isArray(pricesByDates) && pricesByDates.length) {
      setMaxPrice(Math.max(...pricesByDates));
      setMinPrice(Math.min(...pricesByDates));
    }
  }, [pricesByDates]);

  return (
    <>
      <PriceWrapper className="price">
        <PriceTitle>가격 범위</PriceTitle>
        <PriceDisplay>
          ₩{prices.최저가.toLocaleString('en')}~₩
          {prices.최고가.toLocaleString('en')}
        </PriceDisplay>
        {isLoading && (
          <div className={classes.root}>
            <CircularProgress />
          </div>
        )}
        <PriceSlider min={minPrice} max={maxPrice} />
      </PriceWrapper>
    </>
  );
};

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    '& > * + *': {
      marginLeft: theme.spacing(2),
    },
  },
}));

const PriceWrapper = styled.div`
  width: 493px;
  height: 355px;
  padding: 50px;
  background: ${theme.colors.white};

  box-shadow: 0px 4px 10px rgba(51, 51, 51, 0.1),
    0px 0px 4px rgba(51, 51, 51, 0.05);
  border-radius: 40px;
  z-index: -1000;
`;

const PriceTitle = styled.div`
  width: 63px;
  height: 23px;
  padding-bottom: 20px;
  font-weight: bold;
  font-size: 16px;
  line-height: 23px;
  display: flex;
  align-items: center;

  color: ${theme.colors.black};
`;

const PriceDisplay = styled.div`
  width: 75px;
  height: 26px;

  font-size: 18px;
  line-height: 26px;
  display: flex;
  align-items: center;
  color: #333333;
`;
export default PriceModal;
