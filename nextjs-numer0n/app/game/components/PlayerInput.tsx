'use client';

import React, { useState } from 'react';

// プレイヤーの入力情報を表示するコンポーネント
interface PlayerInputProps {
  onSubmit: (input: string) => void;
}

const PlayerInput: React.FC<PlayerInputProps> = ({ onSubmit }) => {
  const [input, setInput] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInput(e.target.value);
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(input);
    setInput('');
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        value={input}
        onChange={handleChange}
        placeholder="3桁の数字を入力して下さい"
      />
      <button type="submit">Submit</button>
    </form>
  );
};

export default PlayerInput;
