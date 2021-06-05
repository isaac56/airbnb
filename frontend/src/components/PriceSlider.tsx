import React, { useCallback, useEffect, useState, useRef } from 'react';
import styled from 'styled-components';
import theme from '../styles/theme';
import { useRecoilState } from 'recoil';
import { filterDisplayData, priceData } from '../recoil/atom';

interface Islider {
  min: number;
  max: number;
}

const PriceSlider: React.FC<Islider> = ({ min, max }) => {
  const [minVal, setMinVal] = useState(0);
  const [maxVal, setMaxVal] = useState(0);
  const minValRef = useRef(min);
  const maxValRef = useRef(max);
  const range = useRef<HTMLInputElement>(null);
  const [price, setPrice] = useRecoilState<any>(priceData);
  const [filterDisplay, setFilterDisplay] =
    useRecoilState<any>(filterDisplayData);
  const priceDisplay = `${minVal.toLocaleString('en')}~${maxVal.toLocaleString(
    'en'
  )}`;
  const guestsCopy = { ...price };

  // Convert to percentage
  const getPercent = useCallback(
    value => Math.round(((value - min) / (max - min)) * 100),
    [min, max]
  );

  useEffect(() => {
    setMinVal(min);
    setMaxVal(max);
  }, [min, max]);

  // Set width of the range to decrease from the left side
  useEffect(() => {
    const minPercent = getPercent(minVal);
    const maxPercent = getPercent(maxValRef.current);
    guestsCopy.최저가 = minVal;
    setPrice({ ...guestsCopy });
    setFilterDisplay({
      ...filterDisplay,
      요금: priceDisplay,
    });

    if (range.current) {
      range.current.style.left = `${minPercent}%`;
      range.current.style.width = `${maxPercent - minPercent}%`;
    }
  }, [minVal, getPercent]);

  // Set width of the range to decrease from the right side
  useEffect(() => {
    const minPercent = getPercent(minValRef.current);
    const maxPercent = getPercent(maxVal);
    guestsCopy.최고가 = maxVal;
    setPrice({ ...guestsCopy });
    setFilterDisplay({
      ...filterDisplay,
      요금: priceDisplay,
    });

    if (range.current) {
      range.current.style.width = `${maxPercent - minPercent}%`;
    }
  }, [maxVal, getPercent]);

  return (
    <Container className="container">
      <input
        type="range"
        min={min}
        max={max}
        value={minVal}
        onChange={event => {
          const value = Math.min(Number(event.target.value), maxVal - 1);
          setMinVal(value);
          minValRef.current = value;
        }}
        className="thumb thumb--left"
      />
      <input
        type="range"
        min={min}
        max={max}
        value={maxVal}
        onChange={event => {
          const value = Math.max(Number(event.target.value), minVal + 1);
          setMaxVal(value);
          maxValRef.current = value;
        }}
        className="thumb thumb--right"
      />

      <Slider>
        <div className="slider__track" />
        <div ref={range} className="slider__range" />
        <div className="slider__left-value">{minVal}</div>
        <div className="slider__right-value">{maxVal}</div>
      </Slider>
    </Container>
  );
};

export default PriceSlider;

const Container = styled.div`
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;

  /* Removing the default appearance */
  .thumb,
  .thumb::-webkit-slider-thumb {
    -webkit-appearance: none;
  }

  .thumb {
    pointer-events: none;
    position: absolute;
    height: 0;
    width: 300px;
    outline: none;
  }

  /* For Chrome browsers */
  .thumb::-webkit-slider-thumb {
    background-color: ${theme.colors.primary};
    border: none;
    border-radius: 50%;
    box-shadow: 0 0 1px 1px ${theme.colors.primary};
    cursor: pointer;
    height: 18px;
    width: 18px;
    margin-top: 4px;
    pointer-events: all;
    position: relative;
  }

  .thumb--left::-webkit-slider-thumb {
    z-index: 3;
  }

  .thumb--right::-webkit-slider-thumb {
    z-index: 4;
  }
`;

const Slider = styled.div`
  position: relative;
  width: 300px;

  .slider__track,
  .slider__range,
  .slider__left-value,
  .slider__right-value {
    position: absolute;
  }

  .slider__track,
  .slider__range {
    border-radius: 3px;
    height: 5px;
  }

  .slider__track {
    background-color: ${theme.colors.grey5};
    width: 100%;
    z-index: 1;
  }

  .slider__range {
    background-color: ${theme.colors.primary};
    z-index: 2;
  }

  .slider__left-value,
  .slider__right-value {
    color: black;
    font-size: 20px;
    margin-top: 20px;
  }

  .slider__left-value {
    left: 6px;
  }

  .slider__right-value {
    right: -4px;
  }
`;
