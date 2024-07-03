type Props = {
  text: string;
};

export default function SubmitButton(props: Props) {
  return (
    <>
      <button
        type="button"
        className="bg-blue-700 rounded-md shadow-md text-white h-8 w-32 hover:bg-blue-600"
      >
        {props.text}
      </button>
    </>
  );
}
