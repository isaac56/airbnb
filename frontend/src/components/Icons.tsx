import React from 'react';
import styled from 'styled-components';
import theme from '../styles/theme';
import { ReactComponent as check } from '../icons/check.svg';
import { ReactComponent as chevronLeft } from '../icons/chevron-left.svg';
import { ReactComponent as chevronRight } from '../icons/chevron-right.svg';
import { ReactComponent as heart } from '../icons/heart.svg';
import { ReactComponent as logo } from '../icons/logo.svg';
import { ReactComponent as menu } from '../icons/menu.svg';
import { ReactComponent as minus } from '../icons/minus.svg';
import { ReactComponent as pauseCircle } from '../icons/pause-circle.svg';
import { ReactComponent as plusCircle } from '../icons/plus-circle.svg';
import { ReactComponent as minusCircle } from '../icons/minus-circle.svg';
import { ReactComponent as plus } from '../icons/plus.svg';
import { ReactComponent as search } from '../icons/search.svg';
import { ReactComponent as user } from '../icons/user.svg';
import { ReactComponent as xCircle } from '../icons/x-circle.svg';

interface Iicon {
  [key: string]: string;
}

const Icon: React.FC<Iicon> = props => {
  if (props.type === 'basicSearch') {
    return (
      <RoundIconWrapper>
        <BasicSearch />
      </RoundIconWrapper>
    );
  } else if (props.type === 'completedSearch') {
    return (
      <RoundIconWrapper className="longer">
        <BasicSearch />
        <SearchLabel>검색</SearchLabel>
      </RoundIconWrapper>
    );
  }

  const IconSvg = () => {
    const defaultSetting = `
  width: ${props.width};
  height: ${props.height};
  stroke: ${props.color};
  position:${props.position};
  right:${props.right};
  top:${props.top};
  margin:${props.margin};
  `;

    switch (props.type) {
      case 'check':
        const Check = styled(check)`
          ${defaultSetting}
        `;
        return (
          <>
            <Check />
          </>
        );
      case 'chevron-left':
        const ChevronLeft = styled(chevronLeft)`
          ${defaultSetting}
        `;
        return (
          <>
            <ChevronLeft />
          </>
        );
      case 'chevron-right':
        const ChevronRight = styled(chevronRight)`
          ${defaultSetting}
        `;
        return (
          <>
            <ChevronRight />
          </>
        );
      case 'heart':
        const Heart = styled(heart)`
          ${defaultSetting}
        `;
        return (
          <>
            <Heart />
          </>
        );
      case 'logo':
        const Logo = styled(logo)`
          ${defaultSetting}
        `;
        return (
          <>
            <Logo />
          </>
        );
      case 'menu':
        const Menu = styled(menu)`
          ${defaultSetting}
        `;
        return (
          <>
            <Menu />
          </>
        );
      case 'minus':
        const Minus = styled(minus)`
          ${defaultSetting}
        `;
        return (
          <>
            <Minus />
          </>
        );
      case 'pause-circle':
        const PauseCircle = styled(pauseCircle)`
          ${defaultSetting}
        `;
        return (
          <>
            <PauseCircle />
          </>
        );
      case 'plus-circle':
        const PlusCircle = styled(plusCircle)`
          ${defaultSetting}
        `;
        return (
          <>
            <PlusCircle />
          </>
        );
      case 'minus-circle':
        const MinusCircle = styled(minusCircle)`
          ${defaultSetting}
        `;
        return (
          <>
            <MinusCircle />
          </>
        );
      case 'plus':
        const Plus = styled(plus)`
          ${defaultSetting}
        `;
        return (
          <>
            <Plus />
          </>
        );
      case 'search':
        const Search = styled(search)`
          ${defaultSetting}
        `;
        return (
          <>
            <Search />
          </>
        );
      case 'user':
        const User = styled(user)`
          ${defaultSetting}
        `;
        return (
          <>
            <User />
          </>
        );
      case 'x-circle':
        const XCircle = styled(xCircle)`
          ${defaultSetting}
        `;
        return (
          <>
            <XCircle />
          </>
        );
      default:
        return <></>;
    }
  };

  return (
    <IconWrapper>
      <IconSvg />
    </IconWrapper>
  );
};

const IconWrapper = styled.div`
  cursor: pointer;
`;

const RoundIconWrapper = styled.div`
  width: 45px;
  height: 43px;
  background-color: ${theme.colors.primary};
  border-radius: 25px;
  padding: 6px;
  cursor: pointer;
  ${theme.alignCenter}
  &.longer {
    width: 90px;
  }
`;

const BasicSearch = styled(search)`
  width: 30px;
`;

const SearchLabel = styled.span`
  color: ${theme.colors.white};
  font-weight: bold;
  font-size: 18px;
  margin-left: 6px;
`;

export default Icon;
