import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Container, Icon, AppBar, Toolbar, Button } from '@material-ui/core';
import { ReactComponent as Logo } from '../icons/logo.svg';
import { ReactComponent as Menu } from '../icons/menu.svg';
import { ReactComponent as User } from '../icons/user.svg';

const Navbar = () => {
  const classes = useStyles();

  return (
    <>
      <AppBar elevation={0} color="transparent" position="sticky">
        <Toolbar className={classes.toolBar}>
          <Logo className={classes.logo} />
          <div>
            <Button className={classes.centerMenu}>숙소</Button>
            <Button className={classes.centerMenu}>체험</Button>
            <Button className={classes.centerMenu}>온라인</Button>
          </div>
          <div className={classes.account}>
            <Menu className={classes.icon} />
            <User className={(classes.icon, classes.iconBackground)} />
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
  logo: {
    height: '32px',
    width: '102px',
    margin: '10px',
    cursor: 'pointer',
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
    width: '30px',
    padding: '2px',
    borderRadius: '13px',
    backgroundColor: 'grey',
    cursor: 'pointer',
  },
  account: {
    borderRadius: '20px',
    backgroundColor: 'white',
    margin: '10px',
    padding: '5px',
    border: '1px solid #BDBDBD',
  },
}));

export default Navbar;
