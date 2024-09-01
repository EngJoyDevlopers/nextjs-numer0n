'use client';

// app/game/singleplayer/page.tsx
import React from 'react';
import GameBoard from '../components/GameBoard';
import GameStatus from '../components/GameStatus';
import PlayerInput from '../components/PlayerInput';

// ランダムな3桁の整数を作成
const number = Math.floor(Math.random() * 1000);

const SinglePlayPage: React.FC = () => {
  const [gameState, setGameState] =
    React.useState('ゲームが開始されていません');
  const [status, setStatus] = React.useState('');

  const handlePlayerInput = (input: string) => {
    // プレイヤーの入力に対する処理をここに追加
    setGameState(`プレイヤーの入力: ${input}`);
    setStatus('入力を処理中...');
  };

  return (
    <main className="flex min-h-screen flex-col p-6 grid place-items-center">
      <h1 className="text-6xl w-full text-center">個人対戦ページ</h1>
      <GameBoard gameState={gameState} />
      <PlayerInput onSubmit={handlePlayerInput} />
      <GameStatus status={status} />
    </main>
  );
};

export default SinglePlayPage;
