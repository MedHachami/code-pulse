// import React from 'react';
// import VSCode from 'Components/vscode';
import { ThemeProvider } from 'styled-components';
import { BrowserRouter as Router } from 'react-router-dom';
import { GlobalStyle, themes } from './Components/themecontext';
import VSCode from './Components/VSCode';
import Loading from './Components/Loading';
import { useState, useEffect } from 'react';

const App = () => {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    //fetching code from backend
    const timer = setTimeout(() => {
      setIsLoading(false);
    }, 2000);

    return () => clearTimeout(timer);
  }, []);

  return (
    <Router>
      <ThemeProvider theme={themes.dark}>
        <GlobalStyle />
        {isLoading ? <Loading /> : <VSCode />}
      </ThemeProvider>
    </Router>
  );
};

export default App;
