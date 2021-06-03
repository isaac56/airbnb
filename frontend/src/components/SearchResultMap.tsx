import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import Icons from './Icons';

declare global {
  interface Window {
    kakao: any;
  }
}

const SearchResultMap = () => {
  const [kakaoMap, setKakaoMap] = useState<any | null>(null);
  useEffect(() => {
    let container = document.getElementById('map');
    let options = {
      center: new window.kakao.maps.LatLng(33.450701, 126.570667),
      level: 3,
    };

    let map = new window.kakao.maps.Map(container, options);
    setKakaoMap(map);
  }, []);
  function zoomIn() {
    kakaoMap.setLevel(kakaoMap.getLevel() - 1);
  }

  function zoomOut() {
    kakaoMap.setLevel(kakaoMap.getLevel() + 1);
  }
  return (
    <>
      <Div>
        <div id="map" style={{ width: '50vw', height: '1024px' }} />
      </Div>
      <ZoomControlWrapper>
        <div onClick={zoomIn}>
          <Icons type="plus" />
        </div>
        <div onClick={zoomOut}>
          <Icons type="minus" />
        </div>
      </ZoomControlWrapper>
    </>
  );
};

const Div = styled.div``;

const ZoomControlWrapper = styled.div`
  position: absolute;
  right: 30px;
  top: 45px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 8px;
  cursor: pointer;
  /* White */

  background: #ffffff;
  /* up2 */

  box-shadow: 0px 4px 10px rgba(51, 51, 51, 0.1),
    0px 0px 4px rgba(51, 51, 51, 0.05);
  border-radius: 8px;
  z-index: 999;
`;

export default SearchResultMap;
