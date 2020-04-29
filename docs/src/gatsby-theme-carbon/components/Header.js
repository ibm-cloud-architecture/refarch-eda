import React from 'react';
import Header from 'gatsby-theme-carbon/src/components/Header';

const CustomHeader = props => (
  <Header {...props}>
    <span>IBM Garage</span>&nbsp;Event-Driven Reference Architecture
  </Header>
);

export default CustomHeader;
