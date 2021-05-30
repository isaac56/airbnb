import React, { useRef, useReducer } from 'react';
import styled from 'styled-components';
import theme from '../styles/theme';
import CalendarDiv from './CalendarDiv';
import Icons from './Icons';

const CalendarModal: React.FC = () => {
  const directionRef = React.useRef<HTMLDivElement>(null);

  let currentType: string | null = null;

  const setStyle = (duration: string, move: string): void => {
    const myRef: HTMLDivElement | null = directionRef.current;
    if (myRef) {
      myRef.style.transition = duration;
      myRef.style.transform = move;
    }
  };

  const moveSlide = (type: string): void => {
    if (type === 'right') {
      setStyle('all 0.7s', `translate(-872px)`);
    } else {
      setStyle('all 0.7s', `translate(872px)`);
    }
    currentType = type;
  };

  const reducer = (state: number, action: { type: string }) => {
    switch (action.type) {
      case 'INCREMENT':
        return state + 2;
      case 'DECREMENT':
        return state - 2;
      default:
        return state;
    }
  };

  const [monthCount, dispatch] = useReducer(reducer, 0);

  const onTransitionEnd = () => {
    const myRef: HTMLDivElement | null = directionRef.current;
    if (myRef) {
      myRef.style.transition = 'none';
      myRef.style.transform = 'translate(0)';
    }

    currentType === 'right' && dispatch({ type: 'INCREMENT' });
    currentType === 'left' && dispatch({ type: 'DECREMENT' });
  };

  const Calendars: React.FC = () => {
    const slideCountsArr: undefined[] = [...Array(6)];
    return (
      <>
        {slideCountsArr.map((v, i) => {
          return <CalendarDiv key={i} month={monthCount + i - 2} />;
        })}
      </>
    );
  };

  return (
    <CalenaderWrapper className="calendar">
      <ArrowWrapper>
        <div
          onClick={() => {
            moveSlide('left');
          }}>
          <Icons type="chevron-left" />
        </div>
        <div
          onClick={() => {
            moveSlide('right');
          }}>
          <Icons type="chevron-right" />
        </div>
      </ArrowWrapper>
      <CarouselWrapper
        onTransitionEnd={onTransitionEnd}
        ref={directionRef as any}>
        <Calendars />
      </CarouselWrapper>
    </CalenaderWrapper>
  );
};

const CalenaderWrapper = styled.div`
  position: relative;
  width: 916px;
  height: 512px;
  padding: 40px 50px;
  background: ${theme.colors.white};
  box-shadow: 0px 4px 10px rgba(51, 51, 51, 0.1),
    0px 0px 4px rgba(51, 51, 51, 0.05);
  border-radius: 40px;
  display: flex;
  justify-content: center;
  overflow: hidden;
`;

const ArrowWrapper = styled.div`
  position: absolute;
  top: 80px;
  width: 816px;
  display: flex;
  justify-content: space-between;
  z-index: 3;
`;

const CarouselWrapper = styled.div`
  display: flex;
`;

export default CalendarModal;
