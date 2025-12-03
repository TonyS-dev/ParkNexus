import { useState } from 'react';

export const useAuth = () => {
  const [token, setToken] = useState<string | null>(null);
  return { token, setToken };
};

