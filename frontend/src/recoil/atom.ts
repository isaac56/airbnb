import { atom, RecoilState } from 'recoil';

const filterAtom = atom({
  key: 'filters',
  default: {
    체크인: '입력 날짜',
    체크아웃: '입력 날짜',
    요금: '금액대 설정',
    인원: '게스트 추가',
  },
});

// interface IdateAtom {
//   key: string;
//   default: {
//     checkIn: Date | null;
//     checkOut: Date | null;
//   };
// }

const datesAtom: RecoilState<any> = atom({
  key: 'dates',
  default: {
    checkIn: null,
    checkOut: null,
  },
});

export { filterAtom, datesAtom };
