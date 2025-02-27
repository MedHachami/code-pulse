import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import {
   Route, useLocation, useNavigate, Routes
} from 'react-router-dom';
import { ResizableBox } from 'react-resizable';
import FilesPane from './filespane';
import LeftNav from './leftnav';
// import BottomBar from './bottombar';
// import CodeArea from './codearea';
// import SearchPane from './searchpane';
// import ExtensionPane from './extensionpane';
// import GitPane from './gitpane';
// import DebuggerPane from './debuggerpane';

const ContentWrapper = styled.div`
  display: flex;
  flex-direction: column;
  height: 99.9vh;
  width: 100%;
  background: red;
  background-color: black;
  color: white;
`;

const Content = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
`;

const CustomHandle = styled.div.attrs({
  className: 'custom-handle'
})`
  height: 100%;
  cursor: col-resize;
  width: 2px;
`;

interface ResizeHandleProps {
  size: {
    width: number;
  };
}

const VSCode: React.FC = () => {
  console.log("inid");
  
  const [file, setFile] = useState<string>('html');
  const [width, setWidth] = useState<number>(240);
  const [initWidth, setInitWidth] = useState<number>(148);
  const currentLocation = useLocation();
  const navigate = useNavigate();
  const { innerWidth } = window;

  const updateWidths = (pathname: string): void => {
    if (innerWidth < 500 && pathname.slice(1) !== '') {
      setWidth(148);
      setInitWidth(150);
    } else if (innerWidth < 500 && pathname.slice(1) === '') {
      setWidth(0);
      setInitWidth(2);
    } else if (innerWidth > 500 && pathname.slice(1) !== '') {
      setWidth(238);
      setInitWidth(240);
    } else {
      setWidth(0);
      setInitWidth(0);
    }
  };

  useEffect(() => {
    updateWidths(currentLocation.pathname);
    navigate(currentLocation.pathname);
  }, [currentLocation.pathname]);

  const onResize = (_event: React.SyntheticEvent, { size }: ResizeHandleProps): void => {
    setWidth(size.width);
  };

  const ResizablePane: React.FC<{ children: React.ReactNode }> = ({ children }) => (
    <ResizableBox
      onResize={onResize}
      style={{ background: 'black', display: 'flex' }}
      width={initWidth}
      height={Infinity}
      handle={<CustomHandle />}
      handleSize={[8, 8]}
    >
      {children}
    </ResizableBox>
  );

  return (
    <ContentWrapper>
      <Content>
        <LeftNav />
        <Routes>
          <Route path="/" element={null} />
          <Route path="/files" element={
            <ResizablePane>
              <FilesPane
                paneWidth={width}
                openFile={(path: string) => setFile(path)}
                toggleCurrentFile={(path: string) => setFile(path)}
              />
            </ResizablePane>
          } />
        </Routes>

        {/* <Route path="/search" exact>
          <ResizablePane>
            <SearchPane paneWidth={width} />
          </ResizablePane>
        </Route>

        <Route path="/git" exact>
          <ResizablePane>
            <GitPane paneWidth={width} />
          </ResizablePane>
        </Route>

        <Route path="/debugger" exact>
          <ResizablePane>
            <DebuggerPane paneWidth={width} />
          </ResizablePane>
        </Route>

        <Route path="/extension" exact>
          <ResizablePane>
            <ExtensionPane paneWidth={width} />
          </ResizablePane>
        </Route> */}

        {/* <CodeArea
          paneWidth={width}
          openFile={file}
          toggleCurrentFile={setFile}
        /> */}
      </Content>
      {/* <BottomBar /> */}
    </ContentWrapper>
  );
};

export default VSCode;