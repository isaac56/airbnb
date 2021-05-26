import { atom } from 'recoil';

const filterAtom = atom({
  key: 'filters',
  default: {
    체크인: '입력 날짜',
    체크아웃: '입력 날짜',
    요금: '금액대 설정',
    인원: '게스트 추가',
  },
});

const datesAtom = atom({
  key: 'dates',
  default: {
    checkIn: {
      year: 0,
      month: 0,
      day: 0,
    },
    checkOut: {
      year: 0,
      month: 0,
      day: 0,
    },
  },
});

export { filterAtom };
