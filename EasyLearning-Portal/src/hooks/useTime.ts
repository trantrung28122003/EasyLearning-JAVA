const getToday = () => {
  const date = new Date();
  return date.toISOString().slice(0, 10);
};

export { getToday };
