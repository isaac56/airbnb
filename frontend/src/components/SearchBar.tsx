import React from 'react';
import { makeStyles } from '@material-ui/core/styles';

import { Box, Grid } from '@material-ui/core';

const SearchBar = () => {
  const classes = useStyles();

  function SearchBarParts(title = 'hi', explanation = 'hi') {
    return (
      <>
        <Box display="flex" flexDirection="colum">
          <div>{title}</div>
          <div>{explanation}</div>
        </Box>
      </>
    );
  }

  return (
    <Grid container alignItems="center" justify="center">
      <Grid container item className={classes.searchBar}>
        <SearchBarParts />
      </Grid>
    </Grid>
  );
};

const useStyles = makeStyles(theme => ({
  searchBar: {
    backgroundColor: 'white',
    width: '80%',
    height: '76px',
    borderRadius: '60px',
  },
}));

export default SearchBar;
