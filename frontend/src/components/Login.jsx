import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import qs from 'qs';
import axios from 'axios';
import Main from './Main';

const Login = ({ history, location }) => {
  useEffect(() => {
    const getToken = async () => {
      const { code } = qs.parse(location.search, {
        ignoreQueryPrefix: true,
      });

      try {
        const response = await axios.get(
          `http://3.35.85.246:8080/api/user/login/oauth/github?code=${code}`
        );

        localStorage.setItem('token', response.data.data);
        history.push('/');
      } catch (error) {}
    };

    getToken();
  }, [location, history]);

  return <Main />;
};

export default Login;
