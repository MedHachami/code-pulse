import React from 'react';

import {
  HTMLIcon, JSIcon, CSSIcon, ReactIcon, JSONIcon,
} from '../icons/icons';

// Define the valid icon types
export type IconType = 'js' | 'css' | 'jsx' | 'json' | 'html';

const renderIcons = (icon: IconType): React.ReactElement | null => {
  switch (icon) {
    case 'js':
      return <JSIcon />;
    case 'css':
      return <CSSIcon />;
    case 'jsx':
      return <ReactIcon />;
    case 'json':
      return <JSONIcon />;
    case 'html':
      return <HTMLIcon />;
    default:
      return null;
  }
};

export default renderIcons;