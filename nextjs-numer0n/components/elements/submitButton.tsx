import Link from 'next/link';

type Props = {
  text: string;
  href: string;
};

export default function SubmitButton({ text, href }: Props) {
  return (
    <Link href={href}>
      <button
        type="button"
        className="bg-blue-700 rounded-md shadow-md text-white h-8 w-32 hover:bg-blue-600"
      >
        {text}
      </button>
    </Link>
  );
}
