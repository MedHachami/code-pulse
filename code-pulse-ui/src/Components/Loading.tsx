import { styled } from 'styled-components';

const LoaderContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
  background: rgba(30, 30, 30, 100%);
`;

const Content = styled.div`
  display: flex;
  width: 100%;
  height: calc(100vh - 25px);
`;

const Bottombar = styled.div`
  height: 25px;
  display: flex;
  justify-content: space-between;
  background: rgba(46, 132, 213, 100%);
`;

const Leftnav = styled.div`
  width: 50px;
  align-content: center;
  height: 100%;
  background: rgba(58, 58, 58, 100%);
  display: flex;
  flex-direction: column;
`;

const NavItem = styled.div`
  margin: 10px 9px;
  background: rgba(124, 124, 124);
  height: 32px;
  width: 32px;
  border-radius: 5px;
  animation: shine infinite ease 1.5s;

  @keyframes shine {
    0% { opacity: 0.8; }
    50% { opacity: 0.5; }
    100% { opacity: 0.8; }
  }
`;

const CodeArea = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

const LoadingText = styled.div`
  color: white;
  font-family: sans-serif;
  margin-top: 10px;
`;

const LoadingSpinner = styled.div`
  width: 20px;
  height: 20px;
  border-radius: 50%;
  border: 4px solid rgba(124, 124, 124, 80%);
  border-top-color: rgba(0, 0, 0, 0.3);
  animation: loading 1.2s linear infinite;

  @keyframes loading {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
`;

const IconsContainer = styled.div`
  display: flex;
`;

const Icon = styled.div`
  height: 18px;
  width: 18px;
  border-radius: 2px;
  margin: 3.5px;
  background: white;
  animation: shine infinite ease 1.5s;
`;

const Loading = () => (
  <LoaderContainer>
    <Content>
      <Leftnav>
        {[...Array(5)].map((_, i) => (
          <NavItem key={i} />
        ))}
      </Leftnav>
      <CodeArea>
        <LoadingSpinner />
        <LoadingText>Loading</LoadingText>
      </CodeArea>
    </Content>
    <Bottombar>
      <IconsContainer>
        {[...Array(4)].map((_, i) => (
          <Icon key={i} />
        ))}
      </IconsContainer>
      <IconsContainer>
        {[...Array(5)].map((_, i) => (
          <Icon key={i} />
        ))}
      </IconsContainer>
    </Bottombar>
  </LoaderContainer>
);

export default Loading;