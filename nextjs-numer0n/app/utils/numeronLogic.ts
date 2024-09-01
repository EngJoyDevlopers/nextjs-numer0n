// 推定する値
const correctDigits = Math.floor(Math.random() * 1000)
  .toString()
  .split('');

// 入力が正しいかどうかを検証する関数
export function checkGuess(input: string) {
  // 三桁であること、数値以外が含まれていないことを確認
  if (input.length !== 3 || isNaN(Number(input))) {
    return {
      message: '3桁の数字を入力してください。',
      eatCount: 0, //
      biteCount: 0
    };
  }

  const inputDigits = input.split('');

  let eatCount = 0;
  let biteCount = 0;

  inputDigits.forEach((digit, index) => {
    if (correctDigits[index] === digit) {
      eatCount++;
    } else if (correctDigits.includes(digit)) {
      biteCount++;
    }
  });

  const message =
    eatCount === 3
      ? '正解です！おめでとう！'
      : `${eatCount}EAT、${biteCount}BITE`;

  return { message, eatCount, biteCount };
}
