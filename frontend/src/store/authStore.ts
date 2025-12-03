import { useState } from 'react';

export const useAuthStore = () => {
  const [user, setUser] = useState<any>(null);
  return { user, setUser };
};

