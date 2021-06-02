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
  }
`;

export default Main;
