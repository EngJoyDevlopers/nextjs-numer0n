import SubmitButton from '@/components/elements/submitButton';

const SetupPage = () => {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen p-6 bg-gray-100">
      <h1 className="text-4xl font-bold mb-8 text-center">
        対戦形式を選択してください
      </h1>
      <div className="flex space-x-4">
        <SubmitButton
          text="練習モード"
          href="./game/singleplayer"
        ></SubmitButton>
        <SubmitButton text="通信対戦" href="./game/multiplayer"></SubmitButton>
      </div>
    </div>
  );
};

export default SetupPage;
