import React from 'react';
import styled from 'styled-components';
import NavBar from './NavBar';
import SearchBar from './SearchBar';

const Main = () => {
  return (
    <>
      <HeroImage>
        <img alt="hero_image" src={`http://localhost:3000/hero_image.webp`} />
      </HeroImage>
      <NavBar />
      <SearchBar />
    </>
  );
};

const HeroImage = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  min-width: 100%;
  min-height: 100%;
  z-index: -1;
  img {
    min-width: 100%;
    min-height: 100%;
    //sm
    /* height: 488px;
    width: 375px; */
    //md
    /* height: 588px;
    width: 744px; */
    //lg
    /* height: 564px;
    width: 1128px; */
  }
`;

export default Main;
