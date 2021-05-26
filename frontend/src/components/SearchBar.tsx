import React, { useState, useEffect } from 'react';
import { Box } from '@material-ui/core';
import { useRecoilValue } from 'recoil';
import { filterAtom } from '../recoil/atom';
import styled from 'styled-components';
import theme from '../styles/theme';
import Icons from './Icons';
import SearchBarFilterStyle from './SearchBarFilterStyle';
import CalendarModal from './CalendarModal';

interface IisOpen {
  체크인: boolean;
  체크아웃: boolean;
  요금: boolean;
  인원: boolean;
}

const SearchBar: React.FC = () => {
  const [isOpen, setisOpen] = useState<IisOpen>({
    체크인: false,
    체크아웃: false,
    요금: false,
    인원: false,
  });

  const detectListener = ({ target }: Event) => {
    isOpen.체크인 &&
      !(target as HTMLInputElement).closest('.calendar, .체크인') &&
      changeIsOpen('체크인');
    isOpen.체크아웃 &&
      !(target as HTMLInputElement).closest('.calendar, .체크아웃') &&
      changeIsOpen('체크아웃');
  };

  useEffect(() => {
    document.addEventListener('mousedown', detectListener);
    return () => document.removeEventListener('mousedown', detectListener);
  });

  const contents = useRecoilValue(filterAtom);

  const changeIsOpen = (className: string): void => {
    const CopiedIsOpen = isOpen;
    CopiedIsOpen[`${className}`] = !CopiedIsOpen[`${className}`];
    setisOpen({
      ...isOpen,
      체크인: CopiedIsOpen.체크인,
      체크아웃: CopiedIsOpen.체크아웃,
      요금: CopiedIsOpen.요금,
      인원: CopiedIsOpen.인원,
    });
  };

  const onClick = (className: string): void => {
    changeIsOpen(className);
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      justifyContent="center"
      alignItems="center">
      <SearchBarWrapper>
        {Object.entries(contents).map(([key, value], i) => {
          return (
            <SearchBarFilterStyle
              key={i}
              label={key}
              text={value}
              onClick={onClick}
            />
          );
        })}
        <SearchIconWrapper>
          <Icons type="basicSearch" />
          {/* <Icons type="completedSearch" /> */}
        </SearchIconWrapper>
      </SearchBarWrapper>
      {(isOpen.체크인 || isOpen.체크아웃) && <CalendarModal />}
    </Box>
  );
};

const SearchBarWrapper = styled.div`
  display: flex;
  align-items: center;
  border-radius: 60px;
  background-color: ${theme.colors.white};
  align-content: stretch;
  width: 916px;
  & > :not(:last-child, :first-child, :hover):after {
    content: '';
    position: absolute;
    transform: translate(-60px, 15px);
    width: 1px;
    height: 50px;
    background-color: ${theme.colors.grey5};
    z-index: 1;
  }
  & > * {
    flex: 1 1 0;
  }
`;

const SearchIconWrapper = styled.div`
  width: 100px;
  display: flex;
  justify-content: flex-end;
  padding-right: 30px;
`;

export default SearchBar;
