# react-native-raw-image

Convert raw images (.dng, .nef, .rw2, .cr2 and .arw tested) to png, only android implemented.

*Personal project not recommended for production.*

## Installation

```sh
npm install react-native-raw-image
```

## Usage

```ts
rawToPng(path: string) => Promise<string | null>;
```

```ts
import { rawToPng } from 'react-native-raw-image';

rawToPng('file:///data').then(photo => {
    // photo saved at cache folder
});
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
