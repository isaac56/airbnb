import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import Icons from './Icons';

declare global {
  interface Window {
    kakao: any;
  }
}

const Map: React.FC = () => {
  const [kakaoMap, setKakaoMap] = useState<any | null>(null);
  useEffect(() => {
    let container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
    let options = {
      //지도를 생성할 때 필요한 기본 옵션
      center: new window.kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
      level: 3, //지도의 레벨(확대, 축소 정도)
    };

    let map = new window.kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
    let zoomControl = new window.kakao.maps.ZoomControl();
    map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);
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
        <div id="map" style={{ width: '708px', height: '1024px' }} />
      </Div>
      <Span onClick={zoomIn}>hi</Span>
    </>
  );
};

const Div = styled.div``;
const Span = styled.span`
  position: absolute;
  bottom: 140px;
  color: red;
  width: 30px;
  font-size: 20px;
  z-index: 999;
  cursor: pointer;
`;

export default Map;
