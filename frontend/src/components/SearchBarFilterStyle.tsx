import React from 'react';
import theme from '../styles/theme';
import styled from 'styled-components';

interface Prop {
  label: string;
  text: string;
  onClick(className: string): void;
}

const SearchBarFilterStyle = (props: Prop) => {
  return (
    <Div
      className={props.label}
      onClick={() => {
        props.onClick(props.label);
      }}>
      <Input>
        <Label>{props.label}</Label>
        <Text>{props.text}</Text>
      </Input>
    </Div>
  );
};
const Div = styled.div`
  padding: 0 5%;
  display: flex;
  flex-direction: flex-start;
  cursor: pointer;
  &:hover {
    background-color: ${theme.colors.grey6};
    border-radius: 60px;
    z-index: 2;
  }
`;
const Input = styled.div`
  display: flex;
  flex-direction: column;
  padding: 16px 12px 16px 12px;
`;
const Label = styled.span`
  color: ${theme.colors.black};
  font-size: 12px;
  font-weight: bold;
  margin-bottom: 10px;
`;
const Text = styled.span`
  color: ${theme.colors.grey2};
  font-size: 16px;
`;

export default SearchBarFilterStyle;
