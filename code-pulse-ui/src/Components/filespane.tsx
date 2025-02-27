import React, { useContext } from 'react';
import styled, { ThemeContext, DefaultTheme } from 'styled-components';

import { ArrowIcon } from '../icons/icons';
import Folder from './folder';

interface FilesWrapperProps {
  paneWidth: number;
  theme: DefaultTheme;
}

const FilesWrapper = styled.div<FilesWrapperProps>`
  width: ${(props) => `${props.paneWidth}px`};
  height: 100%;
  background-color: ${(props) => props.theme.explorer};
  color: white;
  font-weight: 300;
  font-size: 15px;
`;
const Title = styled.div`
  display: flex;
  font-weight: 400;
  font-size: 12px;
  justify-content: space-between;
  padding: 8px;
`;
const MoreInfo = styled.div`
  padding-right: 10px;
`;

const PaneName = styled.span`
  padding-left: 10px;
`;
const RootFolder = styled.div`
  background-color: ${(props) => props.theme.selection};
  display: flex;
  padding: 5px;
`;
const FolderName = styled.div`
  font-weight: 700;
  font-size: 13px;
  padding-left: 5px;
`;
interface FilesPaneProps {
  openFile: (path: string) => void;
  toggleCurrentFile: (path: string) => void;
  paneWidth: number;
}

const FilesPane: React.FC<FilesPaneProps> = ({ openFile, toggleCurrentFile, paneWidth }) => {
  const theme = useContext(ThemeContext);
  return (
    <FilesWrapper paneWidth={paneWidth} theme={theme}>
      <Title>
        <PaneName>EXPLORER</PaneName>
        <MoreInfo>
          <div className="codicon codicon-ellipsis" />
        </MoreInfo>
      </Title>
      <RootFolder  theme={theme}>
        <ArrowIcon isOpen />
        <FolderName>Project Name</FolderName>
      </RootFolder>
      <Folder openFile={openFile} toggleCurrentFile={toggleCurrentFile} />
    </FilesWrapper>
  );
};

export default FilesPane;
