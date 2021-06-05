import { atom, RecoilState } from 'recoil';

const filterDisplayData = atom({
  key: 'display',
  default: {
    체크인: '입력 날짜',
    체크아웃: '입력 날짜',
    요금: '금액대 설정',
    인원: '게스트 추가',
  },
});

const checkInOutData = atom({
  key: 'dates',
  default: {
    체크인: null,
    체크아웃: null,
  },
});

const priceData = atom({
  key: 'prices',
  default: {
    최고가: 0,
    최저가: 0,
  },
});

const guestsData = atom({
  key: 'guests',
  default: {
    성인: 0,
    어린이: 0,
    유아: 0,
  },
});

const mapData = atom({
  key: 'maps',
  default: {},
});

export { filterDisplayData, checkInOutData, priceData, guestsData, mapData };
