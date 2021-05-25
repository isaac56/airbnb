import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { AppBar, Toolbar, Button } from '@material-ui/core';
import Icons from './Icons';

const Navbar: React.FC = () => {
  const classes = useStyles();
  const menus: string[] = ['숙소', '체험', '온라인'];

  return (
    <>
      <AppBar elevation={0} color="transparent" position="sticky">
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
          <div className={classes.account}>
            <Icons type="menu" width="30px" margin="3px 5px" />
            <div className={classes.iconBackground}>
              <Icons type="user" width="30px" />
            </div>
          </div>
        </Toolbar>
      </AppBar>
    </>
  );
};

const useStyles = makeStyles(theme => ({
  toolBar: {
    minHeight: '100px',
    justifyContent: 'space-between',
  },
  centerMenu: {
    fontSize: '16px',
    color: '#222223',
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
}));

export default Navbar;
