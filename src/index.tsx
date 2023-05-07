import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-raw-image' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const RawImage = NativeModules.RawImage
  ? NativeModules.RawImage
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function rawToPng(path: string): Promise<string | null> {
  return RawImage.rawToPng(path);
}
