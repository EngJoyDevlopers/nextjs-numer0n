const eslintPluginReact = require('eslint-plugin-react');
const typescriptParser = require('@typescript-eslint/parser');

module.exports = {
  ignores: ['.next/'],
  languageOptions: {
    parser: typescriptParser,
    parserOptions: {
      ecmaVersion: 2021,
      sourceType: 'module',
      ecmaFeatures: {
        jsx: true
      }
    }
  },
  plugins: {
    react: eslintPluginReact
  },
  rules: {
    'react/react-in-jsx-scope': 'off',
    'react/jsx-filename-extension': [1, { extensions: ['.tsx'] }]
  }
};
