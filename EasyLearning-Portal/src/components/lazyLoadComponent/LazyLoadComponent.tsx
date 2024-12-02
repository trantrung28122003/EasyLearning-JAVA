import React from "react";

const LazyLoadComponent = (lazyComponent: Promise<any>) => {
  const LazyComponent = React.lazy(() => lazyComponent);
  return (
    <React.Suspense fallback={<div>Đang tải ...</div>}>
      <LazyComponent />
    </React.Suspense>
  );
};

export default LazyLoadComponent;
