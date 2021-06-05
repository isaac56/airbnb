import React from 'react';
import styled from 'styled-components';
import theme from '../styles/theme';

const NavBarLogIn = () => {
  const url =
    'https://github.com/login/oauth/authorize?client_id=1047430c9be66344ceb2&scope=user:email&redirect_uri=http://localhost:3000/oauth/login';

  return (
    <LoginWrapper className="login">
      <a href={url}>
        <Span>로그인</Span>
      </a>
    </LoginWrapper>
  );
};

const LoginWrapper = styled.div`
  position: absolute;
  width: 200px;
  /* height: 87px; */
  right: 25px;
  top: 80px;
  padding: 30px 0;
  display: flex;
  align-items: center;
  justify-content: center;

  /* White */

  background: #ffffff;
  /* up2 */

  box-shadow: 0px 4px 10px rgba(51, 51, 51, 0.1),
    0px 0px 4px rgba(51, 51, 51, 0.05);
  border-radius: 10px;
  z-index: 100;
`;

const Span = styled.div`
  width: 136px;
  height: 23px;

  font-size: 16px;

  color: #010101;
`;

export default NavBarLogIn;
