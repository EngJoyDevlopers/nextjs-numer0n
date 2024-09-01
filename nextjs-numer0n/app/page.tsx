import SubmitButton from '@/components/elements/submitButton';
export default function Page() {
  return (
    <main className="flex min-h-screen flex-col p-6 grid place-items-center">
      <h1 className="text-6xl w-full text-center">Numer0n</h1>
      <SubmitButton text="START" href="./setup"></SubmitButton>
    </main>
  );
}
