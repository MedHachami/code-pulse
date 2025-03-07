import React from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';

const HeroContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: calc(100vh - 72px); // Subtract navbar height
  background-color: ${props => props.theme.background};
  color: ${props => props.theme.title};
  text-align: center;
  padding: 2rem;
`;

const Title = styled.h1`
  font-size: 4rem;
  margin-bottom: 1rem;
  color: ${props => props.theme.title};
  text-shadow: 0 0 10px rgba(46, 160, 67, 0.3);
`;

const Subtitle = styled.p`
  font-size: 1.5rem;
  color: ${props => props.theme.text};
  margin-bottom: 2rem;
  max-width: 600px;
`;

const StartButton = styled.button`
  padding: 1rem 2rem;
  font-size: 1.2rem;
  background-color: ${props => props.theme.button};
  color: ${props => props.theme.title};
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;

  &:hover {
    background-color: ${props => props.theme.buttonHover};
    transform: translateY(-2px);
  }
`;

const Hero: React.FC = () => {
  const navigate = useNavigate();

  return (
    <HeroContainer>
      <Title>Code Pulse Editor</Title>
      <Subtitle>
        A powerful, modern code editor in your browser. Edit, collaborate, and
        manage your code with ease.
      </Subtitle>
      <StartButton onClick={() => navigate('/files')}>
        Start Editing
      </StartButton>
    </HeroContainer>
  );
};

export default Hero; 