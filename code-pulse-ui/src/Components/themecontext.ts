import { createGlobalStyle } from 'styled-components';

// Define theme interface
interface Theme {
  bottomBar: string;
  explorer: string;
  bg: string;
  foreground: string;
  selection: string;
  comment: string;
  leftnav: string;
  cyan: string;
  green: string;
  orange: string;
  pink: string;
  purple: string;
  red: string;
  yellow: string;
  background: string;
  title: string;
  text: string;
  button: string;
  border: string;
}

export const themes: Record<'dark' | 'dracula' | 'light', Theme> = {
    dark: {
        bottomBar: 'rgba( 46, 132, 213, 100%)',
        explorer: 'rgba( 41, 41, 42, 100%)',
        bg: 'rgba( 41, 41, 42, 100%)',
        foreground: '#F8F8F2',
        selection: 'rgba( 32, 32, 32, 100%)',
        comment: 'rgba( 138, 138, 138, 100%)',
        leftnav: 'rgba( 58, 58, 58, 100%)',
        cyan: '#8BE9FD',
        green: '#50FA7B',
        orange: '#FFB86C',
        pink: '#FF79C6',
        purple: '#BD93F9',
        red: '#FF5555',
        yellow: '#F1FA8C',
        background: '#272823',
        title: '#6495ed',
        text: '#fff',
        button: 'rgb(106, 115, 125)',
        border: 'rgba( 254, 255, 254, 100%)',
      },
      dracula: {
          bottomBar: '#191a21',
          explorer: '#21222c',
          bg: '#282A36',
          foreground: '#F8F8F2',
          selection: '#44475A',
          comment: '#6272A4',
          cyan: '#8BE9FD',
          green: '#50FA7B',
          orange: '#FFB86C',
          pink: '#FF79C6',
          purple: '#BD93F9',
          red: '#FF5555',
          yellow: '#F1FA8C',
          background: '#272823',
          title: '#6495ed',
          text: '#fff',
          button: 'rgb(106, 115, 125)',
          leftnav: '',
          border: ''
      },
      light: {
          background: '#fff',
          title: '#ff6347',
          text: '#000',
          bottomBar: '',
          explorer: '',
          bg: '',
          foreground: '',
          selection: '',
          comment: '',
          leftnav: '',
          cyan: '',
          green: '',
          orange: '',
          pink: '',
          purple: '',
          red: '',
          yellow: '',
          button: '',
          border: ''
      },
};

export const GlobalStyle = createGlobalStyle`
    * {
      font-family : Source Sans Pro,sans-serif;
      margin: 0;
      padding: 0;
    }
    a{
      text-decoration: none;
      color : #6495ed;
    }
    .CodeEditor{
      width: 100%;
      overflow: hidden;
      margin : 0 !important;
      padding : 0 !important;
      height : 100%;
      background : #44475A !important;
    }
`;