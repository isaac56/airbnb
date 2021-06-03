import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { AppBar, Toolbar, Button } from '@material-ui/core';
import Icons from './Icons';
import NavBarLogin from './NavBarLogin';
import SearchBar from './SearchBar';

interface IisOpen {
  [key: string]: boolean;
}

const Navbar: React.FC = () => {
  const [isOpen, setisOpen] = useState<IisOpen>({
    체크인: false,
    체크아웃: false,
    요금: false,
    인원: false,
    메뉴: false,
  });

  const classes = useStyles();
  const menus: string[] = ['숙소', '체험', '온라인'];

  const detectListener = ({ target }: Event) => {
    Object.entries(isOpen).forEach(([key, value], i) => {
      const classNames = {
        체크인: 'calendar',
        체크아웃: 'calendar',
        요금: 'price',
        인원: 'guests',
        메뉴: 'login',
      };
      isOpen[`${key}`] &&
        !(target as HTMLInputElement).closest(`.${classNames[key]}, .${key}`) &&
        changeIsOpen(`${key}`);
    });
  };

  useEffect(() => {
    document.addEventListener('mousedown', detectListener);
    return () => document.removeEventListener('mousedown', detectListener);
  });

  const changeIsOpen = (className: string) => {
    const CopiedIsOpen = isOpen;
    CopiedIsOpen[`${className}`] = !CopiedIsOpen[`${className}`];
    setisOpen({
      ...isOpen,
      체크인: CopiedIsOpen.체크인,
      체크아웃: CopiedIsOpen.체크아웃,
      요금: CopiedIsOpen.요금,
      인원: CopiedIsOpen.인원,
      메뉴: CopiedIsOpen.메뉴,
    });
  };

  const onClick = (className: string) => {
    changeIsOpen(className);
  };

  return (
    <>
      <AppBar className={classes.appBar} position="fixed">
        <Toolbar className={classes.toolBar}>
          <Icons type="logo" height="32px" width="102px" margin="10px" />
          <div>
            {menus.map((menu, i) => {
              return (
                <Button key={i} className={classes.centerMenu}>
                  {menu}
                </Button>
              );
            })}
          </div>

          <div
            onClick={() => onClick('메뉴')}
            className={`${classes.account} 메뉴`}>
            <Icons type="menu" width="30px" margin="3px 5px" />
            <div className={classes.iconBackground}>
              <Icons type="user" width="30px" />
            </div>
          </div>
          {isOpen.메뉴 && <NavBarLogin />}
        </Toolbar>
        <SearchBar changeModalStatus={onClick} isOpen={isOpen} />
      </AppBar>
    </>
  );
};

const useStyles = makeStyles(theme => ({
  appBar: {
    boxShadow: 'none',
    backgroundColor: 'transparent',
    position: 'sticky',
  },
  toolBar: {
    minHeight: '100px',
    justifyContent: 'space-between',
  },
  centerMenu: {
    fontSize: '16px',
    color: '#222223',
    cursor: 'pointer',
    '&:hover': {
      textDecorationLine: 'underline',
      fontWeight: 'bold',
    },
  },
  icon: {
    width: '30px',
    margin: '0 5px',
    cursor: 'pointer',
  },
  iconBackground: {
    width: '35px',
    padding: '3px',
    borderRadius: '17px',
    backgroundColor: 'grey',
    cursor: 'pointer',
  },
  account: {
    display: 'flex',
    width: '95px',
    borderRadius: '25px',
    backgroundColor: 'white',
    padding: '6px 3px 3px 8px',
    border: '0.1px solid #BDBDBD',
  },
  rightMenu: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'flex-end',
    marginTop: '25px',
  },
}));

export default Navbar;
