'use client';
import React from 'react';

// プレイヤーのターンなど、ゲームの状況を表示するコンポーネント
interface GameStatusProps {
  status: string;
}

const GameStatus: React.FC<GameStatusProps> = ({ status }) => {
  return (
    <div className="game-status">
      <p>{status}</p>
    </div>
  );
};

export default GameStatus;
