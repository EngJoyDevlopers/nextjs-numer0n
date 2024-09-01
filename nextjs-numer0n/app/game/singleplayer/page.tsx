'use client';

import React, { useState } from 'react';
import { checkGuess } from '../../utils/numeronLogic';
import GameBoard from '../components/GameBoard';
import GameStatus from '../components/GameStatus';
import PlayerInput from '../components/PlayerInput';

const SinglePlayPage: React.FC = () => {
  const [gameState, setGameState] = useState('');
  const [status, setStatus] = useState('');
  const [correctNumber, setCorrectNumber] = useState<string>('');
  const [attempts, setAttempts] = useState<number>(0);

  // ゲームの初期化
  React.useEffect(() => {
    // ランダムな3桁の整数を生成して設定
    const randomNumber = (Math.floor(Math.random() * 900) + 100).toString();
    setCorrectNumber(randomNumber);
    setGameState('ゲームが開始されました。');
    setStatus('');
  }, []);

  const handlePlayerInput = (input: string) => {
    if (correctNumber === '') {
      setStatus('ゲームが開始されていません。');
      return;
    }

    const { message, eatCount } = checkGuess(input);

    setGameState(`プレイヤーの入力: ${input}`);
    setStatus(message);

    if (eatCount === 3) {
      setGameState('ゲームクリア！おめでとう！');
    } else {
      setAttempts((prevAttempts) => prevAttempts + 1);
    }
  };

  return (
    <main className="flex min-h-screen flex-col p-6 grid place-items-center">
      <h1 className="text-6xl w-full text-center">個人対戦ページ</h1>
      <GameBoard gameState={gameState} />
      <PlayerInput onSubmit={handlePlayerInput} />
      <GameStatus status={status} />
      <p>試行回数: {attempts}</p>
    </main>
  );
};

export default SinglePlayPage;
