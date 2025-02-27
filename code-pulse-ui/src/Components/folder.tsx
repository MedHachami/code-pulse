import React, { useContext, useState } from 'react';
import styled, { ThemeContext } from 'styled-components';
import { FolderIcon, ArrowIcon, FolderOpenIcon } from '../icons/icons';
import renderIcons, { IconType } from './rendericons';

interface StyledProps {
  isOpen?: boolean;
  theme: {
    selection: string;
    title: string;
  }
}

const FolderWrapper = styled.div`
  width: 100%;
`;

const FolderNameWrapper = styled.div`
  display: flex;
  height: 20px;
  cursor: pointer;
  font-weight: 400;
  padding: 5px;
`;

const FilesWrapper = styled.div`
`;

const File = styled.div<StyledProps>`
  display: flex;
  padding-left: 25px;
  height: 20px;
  padding-top: 5px;
  padding-bottom: 3px;
  font-weight: 400;
  background: ${(props) => (props.isOpen ? props.theme.selection : null)};
  ${(props) => (props.isOpen ? `border: 1px ${props.theme.title} solid;` : null)}
`;

const GroupText = styled.div`
  padding-left: 5px;
`;

interface FolderProps {
  openFile: (path: string) => void;
  toggleCurrentFile: (path: string) => void;
  currentFile?: string;
}

const Folder: React.FC<FolderProps> = ({ openFile, toggleCurrentFile, currentFile }) => {
  const [showcontents, toggleContents] = useState<boolean>(false);
  const theme = useContext(ThemeContext);
  const fileTypes: IconType[] = ['html', 'js', 'css', 'jsx', 'json'];

  
  return (
    <FolderWrapper>
    <FolderNameWrapper onClick={() => toggleContents(!showcontents)}>
      <ArrowIcon isOpen={showcontents} />
      { showcontents ? <FolderOpenIcon /> : <FolderIcon />}
      <GroupText> contents</GroupText>
    </FolderNameWrapper>
    <FilesWrapper>
      {showcontents && (
        <>
          {fileTypes.map((file) => (
            <File
              key={file}
              theme={theme}
              isOpen={currentFile === file}
              onClick={() => {
                toggleCurrentFile(file);
                openFile(file);
              }}
            >
              {renderIcons(file)}
              <GroupText>
                index.
                {file}
              </GroupText>
            </File>
          ))}
        </>
      )}
    </FilesWrapper>
  </FolderWrapper>
  );
};

export default Folder;