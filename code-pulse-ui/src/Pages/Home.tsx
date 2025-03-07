import React from 'react';
import styled from 'styled-components';
import Navbar from '../Components/Navbar';
import Hero from '../Components/Hero';

const HomeContainer = styled.div`
  min-height: 100vh;
  background-color: ${props => props.theme.background};
`;

const Home: React.FC = () => {
  return (
    <HomeContainer>
      <Navbar />
      <Hero />
    </HomeContainer>
  );
};

export default Home; 